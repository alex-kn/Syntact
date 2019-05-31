package com.alexkn.syntact.domain.usecase.play;

import com.alexkn.syntact.domain.repository.BucketRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ManageScore {


    private static final double LEVELING_COEFFICIENT = 0.16;

    @Inject
    BucketRepository bucketRepository;

    @Inject
    ManageScore() {

    }

    private int calculateLevel(int score) {

        return (int) Math.floor(LEVELING_COEFFICIENT * Math.sqrt(score));
    }

    public int calculateMaxForLevel(int level) {

        return (int) Math.ceil(Math.pow(level / LEVELING_COEFFICIENT, 2));
    }
}
