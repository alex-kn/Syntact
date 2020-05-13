package dev.alexknittel.syntact.service.config

import dagger.Module
import dagger.Provides
import dev.alexknittel.syntact.service.SyntactService
import retrofit2.Response
import javax.inject.Singleton

@Module
class AndroidTestNetworkModule {

    @Singleton
    @Provides
    fun provideSyntactService(): SyntactService = object : SyntactService {

        override suspend fun fetch(url: String, srcLang: String, phrase: String): Response<String> {
            return Response.success("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<body>\n" +
                    "<div class='isForeignTerm'>\n" +
                    "    <div class='example line '>\n" +
                    "        <span class='tag_s'>Sie liest gern, genau wie ich.</span>\n" +
                    "        <span class='tag_t'>She enjoys reading, just like me.</span>\n" +
                    "    </div>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>\n")
        }
    }

}
