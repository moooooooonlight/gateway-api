package com.nhnacademy.gatewayapi.domain.dto;

import com.nhnacademy.gatewayapi.domain.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentListDTO {
    List<Comment> comments;
}