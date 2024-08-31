package br.com.leandro.podcast.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class Feed @JvmOverloads constructor (
    /**
     * @return the channelTitle
     *
     * @param channelTitle the channelTitle to set
     */
    @field:Element(name = "title")
    @param:Element(name = "title")
    @field:Path("channel")
    @param:Path("channel")
    var channelTitle: String? = null,

    /**
     * @return the channelDescription
     *
     * @param channelDescription the channelDescription to set
     */
    @field:Element(name = "description")
    @param:Element(name = "description")
    @field:Path("channel")
    @param:Path("channel")
    var channelDescription: String? = null,

    /**
     * @return the podcastList
     *
     * @param podcastList the podcastList to set
     */
    @field:ElementList(name = "item", inline = true, required = false)
    @param:ElementList(name = "item", inline = true, required = false)
    @field:Path("channel")
    @param:Path("channel")
    var podcastList: List<Podcast>? = null
)