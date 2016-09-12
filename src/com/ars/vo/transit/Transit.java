package com.ars.vo.transit;

import com.ars.domain.schedule.Schedule;

/**
 * Created by JJ on 5/14/16.
 */
public class Transit {
    private int transitNumber;
    private Schedule schedule;

    public int getTransitNumber() {
        return transitNumber;
    }

    public void setTransitNumber(int transitNumber) {
        this.transitNumber = transitNumber;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
