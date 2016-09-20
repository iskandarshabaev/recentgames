package com.recentgames.repository;

import com.recentgames.GamesType;
import com.recentgames.model.content.GameDescription;
import com.recentgames.model.content.GamePreview;
import com.recentgames.model.content.ReviewDescription;

import java.util.List;

import rx.Observable;

public interface GiantBombRepository {

    Observable<GameDescription> game(int gameId);

    Observable<List<GamePreview>> games(@GamesType int type, int offset);

    Observable<List<GamePreview>> search(String name);

    Observable<ReviewDescription> review();

}
