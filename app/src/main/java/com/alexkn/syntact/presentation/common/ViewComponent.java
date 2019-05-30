package com.alexkn.syntact.presentation.common;

import com.alexkn.syntact.app.ApplicationComponent;
import com.alexkn.syntact.presentation.addlanguage.AddLanguageViewModel;
import com.alexkn.syntact.presentation.hangman.HangmanViewModel;
import com.alexkn.syntact.presentation.menu.LanguagesViewModel;
import com.alexkn.syntact.presentation.template.create.TemplateViewModel;
import com.alexkn.syntact.presentation.template.list.TemplateCreateViewModel;

import dagger.Component;

@Component(dependencies = ApplicationComponent.class)
@ActivityScope
public interface ViewComponent {

    void inject(HangmanViewModel hangmanBoardViewModel);

    void inject(LanguagesViewModel languagesViewModel);

    void inject(AddLanguageViewModel addLanguageViewModel);

    void inject(TemplateViewModel templateViewModel);

    void inject(TemplateCreateViewModel templateCreateViewModel);
}
