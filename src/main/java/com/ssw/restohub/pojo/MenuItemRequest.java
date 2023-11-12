package com.ssw.restohub.pojo;

import com.ssw.restohub.data.MenuItem;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItemRequest {
    private MenuItem menuItem;
    private Long restaurantId;
}
