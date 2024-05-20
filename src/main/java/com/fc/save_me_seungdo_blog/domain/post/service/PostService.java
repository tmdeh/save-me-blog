package com.fc.save_me_seungdo_blog.domain.post.service;

import com.fc.save_me_seungdo_blog.domain.auth.model.entity.User;
import com.fc.save_me_seungdo_blog.domain.auth.repository.UserRepository;
import com.fc.save_me_seungdo_blog.domain.post.model.entity.Post;
import com.fc.save_me_seungdo_blog.domain.post.model.request.CreatePostRequest;
import com.fc.save_me_seungdo_blog.domain.post.model.request.GetPostRequest;
import com.fc.save_me_seungdo_blog.domain.post.model.request.UpdatePostReqeust;
import com.fc.save_me_seungdo_blog.domain.post.model.response.PostResponse;
import com.fc.save_me_seungdo_blog.domain.post.repository.PostRepository;
import com.fc.save_me_seungdo_blog.global.exception.CustomApiException;
import com.fc.save_me_seungdo_blog.global.exception.code.AuthErrorCode;
import com.fc.save_me_seungdo_blog.global.exception.code.PostErrorCode;
import com.fc.save_me_seungdo_blog.global.model.response.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Api<PostResponse> create(CreatePostRequest request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new CustomApiException(AuthErrorCode.NOT_FOUND));

        Post post = postRepository.save(
            Post.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .build()
        );

        return Api.SUCCESS(HttpStatus.CREATED, PostResponse.builder()
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .build()
        );
    }


    @Transactional(readOnly = true)
    public Api<Page<PostResponse>> getList(GetPostRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),
            Sort.by(request.getDirection().getValue(),
                request.getSortBy().name()));

        Page<PostResponse> page = postRepository.findAllByTitleContaining(request.getKeyword(),
            pageable).map(post -> PostResponse.builder()
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .build());

        return Api.OK(page);

    }


    @Transactional(readOnly = true)
    public Api<PostResponse> getDetail(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new CustomApiException(PostErrorCode.NOT_FOUND));
        return Api.OK(PostResponse.toResponse(post));
    }

    @Transactional
    public Api<PostResponse> update(UpdatePostReqeust request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new CustomApiException(AuthErrorCode.NOT_FOUND));

        Post post = postRepository.findByIdAndUser(request.getId(), user)
            .orElseThrow(() -> new CustomApiException(PostErrorCode.NOT_FOUND));
        post.update(request);

        return Api.OK(PostResponse.builder()
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .build()
        );
    }

    @Transactional
    public Api<?> delete(long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new CustomApiException(AuthErrorCode.NOT_FOUND));

        Post post = postRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new CustomApiException(PostErrorCode.NOT_FOUND));

        postRepository.delete(post);
        return Api.OK();
    }
}
