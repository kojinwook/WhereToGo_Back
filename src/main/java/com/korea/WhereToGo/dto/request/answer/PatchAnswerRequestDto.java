package com.korea.WhereToGo.dto.request.answer;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchAnswerRequestDto {
    @NotBlank
    private String content;
    @NotBlank
    private String nickname;
    @NotNull
    private Long questionId;


}