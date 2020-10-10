package top.weidaboy.dao;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import top.weidaboy.domain.Post;

/**
 * 公告Post持久层
 * 继承AbstractBaseDao, 使用抽象模版设计模式
 */
@Repository
public class PostDao extends BaseRepository<Post> {
}
