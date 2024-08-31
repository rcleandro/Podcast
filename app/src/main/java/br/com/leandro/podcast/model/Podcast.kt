package br.com.leandro.podcast.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class Podcast @JvmOverloads constructor (

    /**
     * @return the title
     *
     * @param title the title to set
     */
    @field:Element(name = "itunes:title", required = false)
    @param:Element(name = "itunes:title", required = false)
    @field:Namespace(prefix = "itunes", reference = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    @param:Namespace(prefix = "itunes", reference = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    var title: String? = null,

    /**
     * @return the description
     *
     * @param description the description to set
     */
    @field:Element(name = "description", required = false)
    @param:Element(name = "description", required = false)
    var description: String? = null,

    /**
     * @return the pubDate
     *
     * @param pubDate the pubDate to set
     */
    @field:Element(name = "pubDate", required = false)
    @param:Element(name = "pubDate", required = false)
    var pubDate: String? = null,

    /**
     * @return the enclosure
     *
     * @param enclosure the enclosure to set
     */
    @field:Element(name = "enclosure", required = false)
    @param:Element(name = "enclosure", required = false)
    var enclosure: Enclosure? = null
)