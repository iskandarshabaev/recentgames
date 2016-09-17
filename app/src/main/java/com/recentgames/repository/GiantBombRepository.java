package com.recentgames.repository;

import com.recentgames.model.content.GameDescription;
import com.recentgames.model.content.GamePreview;
import com.recentgames.model.content.ReviewDescription;

import java.util.List;

import rx.Observable;

public interface GiantBombRepository {

    Observable<GameDescription> game();

    Observable<List<GamePreview>> games();

    Observable<List<GamePreview>> search();

    Observable<List<ReviewDescription>> review();

}
