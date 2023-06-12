package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.conf.ResponseResult;
import org.example.entity.Comment;

/**
 * 评论表(SgComment)表服务接口
 *
 * @author makejava
 * @since 2023-05-23 18:00:20
 */
public interface SgCommentService extends IService<Comment> {

    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}

