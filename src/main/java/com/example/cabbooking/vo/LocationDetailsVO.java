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
    private int xDistance;
    private int yDistance;

    public int distanceTo(LocationDetailsVO source) {
        return Math.abs(this.xDistance - xDistance) + Math.abs(this.yDistance - yDistance);
    }
}
