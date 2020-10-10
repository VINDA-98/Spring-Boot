package top.weidaboy.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.weidaboy.domain.Post;
import top.weidaboy.service.IPostService;

@Service
public class PostServiceImpl extends BaseServiceImpl<Post> implements IPostService {
}