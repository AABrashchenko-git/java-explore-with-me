package ru.practicum.model.dto.compilation;

import lombok.Data;
import jakarta.validation.constraints.Size;
import ru.practicum.utils.NotBlankIfPresent;

import java.util.List;

@Data
public class UpdateCompilationRequest {
    private List<Long> events;
    private Boolean pinned;
    @Size(min = 1, max = 50)
    @NotBlankIfPresent // отвечаю на твой вопрос из PR почему не использовал общепринятую @notblank
    // руководствовался такой логикой, что при запросе на обновление поле может быть null (то есть его не требуется обновлять)
    //но тогда при использовании NotBlank валидация всего объекта не пройдет, если поле null. Поэтому проверяю на пустоту поля только при его наличии)
    // аналогично в других местах на обновление
    private String title;
}
