package tw.appworks.school.yichien.enabling.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessResponse {

    @JsonProperty("success")
    String message;
}
