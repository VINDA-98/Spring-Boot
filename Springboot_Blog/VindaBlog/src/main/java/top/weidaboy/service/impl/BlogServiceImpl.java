package top.weidaboy.service.impl;

import top.weidaboy.entity.Blog;
import top.weidaboy.mapper.BlogMapper;
import top.weidaboy.service.BlogService;
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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
