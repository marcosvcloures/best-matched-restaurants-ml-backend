package com.example.bestmatchedrestaurants.util

class CSVParser(private val csvText: String)
{
    fun parse() : ArrayList<HashMap<String, String>>
    {
        val response = ArrayList<HashMap<String, String>>()
        var keys : List<String>? = null

        csvText.split("\r\n").forEach {
            val values = it.split(',');

            // it's the first line
            if (keys == null)
            {
                keys = values;
            }
            else
            {
                val line = HashMap<String, String>();

                if (values.size != keys!!.size)
                    throw IllegalArgumentException(String.format("Line size mismatched with header size, line size: %d, header size: %d, line:\n\t%s", values.size, keys!!.size, line))

                for (index in values.indices)
                    line[keys!![index]] = values[index];

                response.add(line);
            }
        }

        return response;
    }
}