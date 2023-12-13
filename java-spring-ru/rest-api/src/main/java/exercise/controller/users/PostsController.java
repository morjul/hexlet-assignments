package exercise.controller.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RestController
@RequestMapping("/api")
public class PostsController {
    private List<Post> posts = new ArrayList<>();

    @GetMapping("/users/{id}/posts") // Вывод страницы
    public List<Post> show(@PathVariable Integer id) {
        var postList = posts.stream()
                .filter(p -> Integer.valueOf(p.getUserId()).equals(id))
                .toList();
        return postList;
    }

    @PostMapping("/users/{id}/posts") // Создание страницы
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@PathVariable Integer id, @RequestBody Post post) {
        Post createdPost = new Post();
        createdPost.setUserId(id);
        createdPost.setTitle(post.getTitle());
        createdPost.setBody(post.getBody());
        createdPost.setSlug(post.getSlug());
        posts.add(createdPost);
        return createdPost;
    }
}

// END
