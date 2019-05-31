package com.alexkn.syntact.presentation.common;

import com.alexkn.syntact.app.ApplicationComponent;
import com.alexkn.syntact.presentation.bucket.create.CreateBucketViewModel;
import com.alexkn.syntact.presentation.play.board.BoardViewModel;
import com.alexkn.syntact.presentation.play.menu.PlayMenuViewModel;
import com.alexkn.syntact.presentation.template.list.ListTemplatesViewModel;
import com.alexkn.syntact.presentation.template.create.CreateTemplateViewModel;

import dagger.Component;

@Component(dependencies = ApplicationComponent.class)
@ActivityScope
public interface ViewComponent {

    void inject(BoardViewModel hangmanBoardViewModel);

    void inject(PlayMenuViewModel playMenuViewModel);

    void inject(CreateBucketViewModel createBucketViewModel);

    void inject(ListTemplatesViewModel listTemplatesViewModel);

    void inject(CreateTemplateViewModel createTemplateViewModel);
}
