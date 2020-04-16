package com.edu.framework.domain.message.ext;

import com.edu.framework.domain.message.McMessage;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class McMessageExt extends McMessage {
    private String type;
}
