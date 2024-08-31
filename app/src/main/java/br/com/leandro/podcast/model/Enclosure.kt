package br.com.leandro.podcast.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = "enclosure", strict = false)
data class Enclosure @JvmOverloads constructor (
    /**
     * @return the url
     *
     * @param url the url to set
     */
    @field:Attribute(name = "url")
    @param:Attribute(name = "url")
    var url: String? = null,

    /**
     * @return the length
     *
     * @param length the length to set
     */
    @field:Attribute(name = "length", required = false)
    @param:Attribute(name = "length", required = false)
    var length: Long? = null,

    /**
     * @return the type
     *
     * @param type the type to set
     */
    @field:Attribute(name = "type", required = false)
    @param:Attribute(name = "type", required = false)
    var type: String? = null
)
