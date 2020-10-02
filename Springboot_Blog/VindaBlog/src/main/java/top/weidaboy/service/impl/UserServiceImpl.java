package top.weidaboy.service.impl;

import top.weidaboy.entity.User;
import top.weidaboy.mapper.UserMapper;
import top.weidaboy.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author From：Vinda
 * @since 2020-09-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
