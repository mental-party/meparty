package com.teammental.merest.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;


public class SortDeserializer extends JsonDeserializer<Sort> {

  private static final Logger LOGGER = LoggerFactory.getLogger(SortDeserializer.class);

  @Override
  public Sort deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {


    try {
      ObjectNode node = jp.getCodec().readTree(jp);
      Sort.Order[] orders = new Sort.Order[node.size()];
      int index = 0;
      for (JsonNode obj : node) {
        orders[index] = new Sort.Order(
            Sort.Direction.valueOf(obj.get("direction").asText()), obj.get("property").asText());
        index++;
      }
      Sort sort = new Sort(orders);
      return sort;
    } catch (Exception ex) {
      LOGGER.error(ex.getLocalizedMessage());

      return Sort.unsorted();
    }
  }
}
