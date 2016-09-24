package com.recentgames.screen.games;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class GamesPresenterTest {

    @Mock
    private GamesView mView;
    private GamesPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        mView = mock(GamesView.class);
        mPresenter = new GamesPresenter(mView);
    }

    @Test
    public void testCreated() throws Exception {
        assertNotNull(mPresenter);
        verifyNoMoreInteractions(mView);
    }

    @Test
    public void searchClickTest() throws Exception {
        mPresenter.onMenuSearchClick();
        verify(mView).openSearchScreen();
    }
}
