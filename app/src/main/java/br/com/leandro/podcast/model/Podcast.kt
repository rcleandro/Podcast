package br.com.leandro.podcast.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class Podcast @JvmOverloads constructor (

    /**
     * @return the title
     *
     * @param title the title to set
     */
    @field:Element(name = "title", required = false)
    @param:Element(name = "title", required = false)
    @field:Path("item")
    @param:Path("item")
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