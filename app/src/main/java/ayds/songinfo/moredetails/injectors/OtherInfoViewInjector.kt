package ayds.songinfo.home.view

import ayds.songinfo.moredetails.injectors

object HomeViewInjector {

    fun init(view: OtherInfoView) {
        RepositorylInjector.initRepository(view)
        OtherInfoPresenterInjector.onViewStarted(view)
    }
}