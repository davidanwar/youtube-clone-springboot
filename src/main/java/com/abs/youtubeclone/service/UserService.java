package com.abs.youtubeclone.service;

import com.abs.youtubeclone.model.User;
import com.abs.youtubeclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class UserService {

    @Value("${auth0.userinfoEndpoint}")
    private String userEndpoint;
    @Autowired
    private UserRepository userRepository;

    public void registerUser(String tokenValue) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(userEndpoint))
                .setHeader("Authorization", String.format("Bearer %s", tokenValue))
                .build();

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        try {
            httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void addToLikedVideos(String videoId) {
        var user = getCurrentUser();
        user.addToLikedVideos(videoId);
        userRepository.save(user);
    }

    public void removeFromLikedVideos(String videoId) {
        var user = getCurrentUser();
        user.removeFromLikedVideos(videoId);
        userRepository.save(user);
    }

    public void removeFromDisLikedVideo(String videoId) {
        var user = getCurrentUser();
        user.removeFromDisLikedVideo(videoId);
        userRepository.save(user);
    }

    public boolean ifLikedVideo(String videoId) {
        return getCurrentUser().getLikedVideo().stream().anyMatch(id -> id.equals(videoId));
    }

    public boolean ifDisLikedVideo(String videoId) {
        return getCurrentUser().getDiskLikeVideo().stream().anyMatch(id -> id.equals(videoId));
    }

    private User getCurrentUser() {
        //String sub = ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaim("sub");
        String dummyId = "12345david";
        return userRepository.findById(dummyId).orElseThrow(() -> new IllegalArgumentException("Cannot find user with sub - "));
    }


    public void createUser(User user) {
        userRepository.save(user);
    }
}
