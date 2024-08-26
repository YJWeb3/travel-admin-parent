package com.zheng.travel.admin.controller.login;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zheng.travel.admin.commons.anno.TravelLog;
import com.zheng.travel.admin.commons.anno.TravelRateLimiter;
import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.ex.TravelBussinessException;
import com.zheng.travel.admin.commons.threadlocal.UserThrealLocal;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import com.zheng.travel.admin.commons.utils.pwd.DesUtils;
import com.zheng.travel.admin.commons.utils.pwd.MD5Util;
import com.zheng.travel.admin.config.interceptor.repeat.RepeatSubmit;
import com.zheng.travel.admin.controller.BaseController;
import com.zheng.travel.admin.pojo.SysLoginUser;
import com.zheng.travel.admin.pojo.Permission;
import com.zheng.travel.admin.pojo.Role;
import com.zheng.travel.admin.redis.config.TravelRedisCacheTemplate;
import com.zheng.travel.admin.service.jwt.JwtBlackStringService;
import com.zheng.travel.admin.service.sysloginuser.ISysLoginUserService;
import com.zheng.travel.admin.utils.jwt.JwtTokenUtils;
import com.zheng.travel.admin.vo.LoginUserVo;
import com.zheng.travel.admin.vo.LoginVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;


@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController extends BaseController {

    private final ISysLoginUserService userService;
    private final TravelRedisCacheTemplate travelRedisCacheTemplate;
    private final JwtBlackStringService jwtBlackListService2;
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 不需要密码
     *
     * @param loginVo
     * @return
     */
    @PostMapping("/auth/login")
    @TravelLog(title = "登录模块-密码登录")
    @RepeatSubmit
    @TravelRateLimiter(timeout = 1, limit = 3)
    public LoginUserVo loginbyname(@RequestBody LoginVo loginVo, HttpSession session) {
        Vsserts.isEmptyEx(loginVo.getUsername(), new TravelBussinessException(ResultStatusEnumA.USER_PWD_STATUS_INPUT));
        Vsserts.isEmptyEx(loginVo.getCode(), new TravelBussinessException(ResultStatusEnumA.USER_CODE_STATUS_INPUT));
        log.info("1----------------------->登录进来了，你输入的用户名{}，密码是：{}，验证码是：{}", loginVo.getUsername(),
                loginVo.getPassword(), loginVo.getCode());
        // 验证码的判断
        String cacheCode = stringRedisTemplate.opsForValue().get(loginVo.getUuid());
        log.info("2----------------------->从服务器redis中获取code：{}", cacheCode);
        Vsserts.isEmptyEx(cacheCode, new TravelBussinessException(ResultStatusEnumA.USER_CODE_ERROR_CACHE));
        if (!cacheCode.equalsIgnoreCase(loginVo.getCode())) {
            throw new TravelBussinessException(ResultStatusEnumA.USER_CODE_INPUT_ERROR);
        }

        // 1: 根据username查询用户信息
        LambdaQueryWrapper<SysLoginUser> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(SysLoginUser::getNickname, loginVo.getUsername());
        SysLoginUser user = userService.getOne(userLambdaQueryWrapper);
        // 如果查询用户存在
        if (user != null) {
            // 2: 创建token
            String token = JwtTokenUtils.createToken(user);
            // 3: 创建vo,按需加载封装返回即可
            LoginUserVo loginUserVo = new LoginUserVo();
            loginUserVo.setUserId(user.getId());
            loginUserVo.setAvatar(user.getAvatar());
            loginUserVo.setNickname(user.getNickname());
            loginUserVo.setToken(token);
            // 4: 把对应用户的角色和权限查询出来
            List<Role> roleList = userService.findSysRoleByUserId(user.getId());
            List<Permission> permissionList = userService.findBySysPermissionUserId(user.getId());
            loginUserVo.setRoleList(roleList);
            loginUserVo.setPermissionList(permissionList);

            // 这里存放只是为了让pugaspect切面进行获取，和后续的业务无关
            UserThrealLocal.put(user);

            // 写入信息到redis缓存中
            String tokenKey = "sys:user:token:" + token;
            travelRedisCacheTemplate.setCacheObject("sys:user:permission:list:" + token, permissionList);
            travelRedisCacheTemplate.setCacheObject(tokenKey, token, JwtTokenUtils.REDIS_TOKEN_EXPIRATION, TimeUnit.MILLISECONDS);
            // 返回即可
            return loginUserVo;
        } else {
            // 代表输入账号密码有误
            throw new TravelBussinessException(ResultStatusEnumA.USER_PWD_STATUS);
        }
    }


    /**
     * @param loginVo
     * @return
     */
    @PostMapping("/auth/login/pwd")
    @TravelLog(title = "登录模块-密码登录")
    @TravelRateLimiter(timeout = 1, limit = 3)
    public LoginUserVo loginbynamepwd(@RequestBody LoginVo loginVo) {
        log.info("1----------------------->登录进来了，你输入的用户名{}，密码是：{}，验证码是：{}", loginVo.getUsername(),
                loginVo.getPassword(), loginVo.getCode());

        Vsserts.isEmptyEx(loginVo.getUsername(), new TravelBussinessException(ResultStatusEnumA.USER_USERNAME_STATUS));
        Vsserts.isEmptyEx(loginVo.getPassword(), new TravelBussinessException(ResultStatusEnumA.USER_PWD_STATUS_INPUT));
        Vsserts.isEmptyEx(loginVo.getCode(), new TravelBussinessException(ResultStatusEnumA.USER_CODE_STATUS_INPUT));
        // 验证码的判断
        String cacheCode = stringRedisTemplate.opsForValue().get(loginVo.getUuid());
        log.info("2----------------------->从服务器redis中获取code：{}", cacheCode);
        Vsserts.isEmptyEx(cacheCode, new TravelBussinessException(ResultStatusEnumA.USER_CODE_ERROR_CACHE));
        if (!cacheCode.equalsIgnoreCase(loginVo.getCode())) {
            throw new TravelBussinessException(ResultStatusEnumA.USER_CODE_INPUT_ERROR);
        }

        // 1: 根据username查询用户信息
        LambdaQueryWrapper<SysLoginUser> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(SysLoginUser::getNickname, loginVo.getUsername());
        SysLoginUser user = userService.getOne(userLambdaQueryWrapper);
        if (user != null) {
            // 把用户输入的密码进行md5假面
            log.info("用户输入的密码是：{},{}", loginVo.getPassword(), DesUtils.decrypt(loginVo.getPassword()));
            String inputpwd = MD5Util.md5slat(DesUtils.decrypt(loginVo.getPassword()));
            // 然后把用户输入的加密的密码和数据库中user.getPassword()进行比较
            if (!inputpwd.equalsIgnoreCase(user.getPassword())) {
                throw new TravelBussinessException(ResultStatusEnumA.USER_PWD_STATUS);
            }
            // 2：生成token
            String token = JwtTokenUtils.createToken(user);
            // 3: 创建vo,按需加载封装返回即可
            LoginUserVo loginUserVo = new LoginUserVo();
            loginUserVo.setUserId(user.getId());
            loginUserVo.setAvatar(user.getAvatar());
            loginUserVo.setNickname(user.getNickname());
            loginUserVo.setToken(token);

            // 4: 把对应用户的角色和权限查询出来
            List<Role> roleList = userService.findSysRoleByUserId(user.getId());
            List<Permission> permissionList = userService.findBySysPermissionUserId(user.getId());
            loginUserVo.setRoleList(roleList);
            loginUserVo.setPermissionList(permissionList);


            // 这里存放只是为了让pugaspect切面进行获取，和后续的业务无关
            UserThrealLocal.put(user);

            // 写入信息到redis缓存中
            String tokenKey = "sys:user:token:" + token;
            travelRedisCacheTemplate.setCacheObject("sys:user:permission:list:" + token, permissionList);
            travelRedisCacheTemplate.setCacheObject(tokenKey, token, JwtTokenUtils.REDIS_TOKEN_EXPIRATION, TimeUnit.MILLISECONDS);
            return loginUserVo;
        } else {
            // 代表输入账号密码有误
            throw new TravelBussinessException(ResultStatusEnumA.USER_PWD_STATUS);
        }
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @PostMapping("/auth/logout")
    @TravelLog(title = "退出登录")
    public String logout(HttpServletRequest request) {
        // 获取请求toekn
        String token = JwtTokenUtils.getJwtToken(request);
        DecodedJWT jwtFromToken = JwtTokenUtils.getJWTFromToken(token);
        if (jwtFromToken != null) {
            String userId = jwtFromToken.getSubject();
            String nickName = jwtFromToken.getIssuer();
            SysLoginUser sysLoginUser = new SysLoginUser();
            sysLoginUser.setId(Long.parseLong(userId));
            sysLoginUser.setNickname(nickName);
            UserThrealLocal.put(sysLoginUser);
        }
        // 把当前token拉入黑名单中
        jwtBlackListService2.addBlackList(token);
        // 删除缓存
        travelRedisCacheTemplate.deleteObject("sys:user:permission:list:" + token);
        // 返回成功
        return "success";
    }
}
