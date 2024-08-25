package com.example.cabbooking.vo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LocationDetailsVO {
    private int x;
    private int y;

    public int distanceTo(LocationDetailsVO source) {
        return Math.abs(this.x - x) + Math.abs(this.y - y);
    }
}
