package com.diagorn.sparkathon.auth.utils

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

/**
 * Utilities to work with JSON
 *
 * @author mikhail.gasin
 */
object JsonUtils {
    private val objectMapper = ObjectMapper()

    init {
        objectMapper.registerModule(JavaTimeModule())
    }

    /**
     * Convert object to JSON
     *
     * @param obj - object
     * @return JSON representation
     */
    fun toJson(obj: Any?): String =
        try {
            objectMapper.writeValueAsString(obj)
        } catch (e: JsonProcessingException) {
            throw RuntimeException(JSON_WRITE_FAILED, e)
        }

    /**
     * Convert JSON to POJO
     *
     * @param json  - JSON to convert
     * @param clazz - class to be converted to
     * @param <T>   - POJO data type
     * @return clazz instance
    </T> */
    fun <T> fromJson(json: String?, clazz: Class<T>): T {
        if (json.isNullOrEmpty()) {
            throw IllegalArgumentException(EMPTY_JSON)
        }

        return try {
            objectMapper.readValue(json, clazz)
        } catch (e: JsonProcessingException) {
            throw RuntimeException(couldNotReadClass(json, clazz.name))
        }
    }

    private fun couldNotReadClass(json: String, className: String) =
        "Could not read value from JSON $json for class $className"

    const val JSON_WRITE_FAILED = "Could not write object as JSON"
    const val EMPTY_JSON = "JSON is empty"
}