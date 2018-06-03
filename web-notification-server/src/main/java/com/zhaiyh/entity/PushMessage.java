package com.zhaiyh.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushMessage {
    private String requestId;
    private List<String> users;
    private List<String> tags;
    private String message;
}
