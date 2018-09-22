package com.teammental.mecore.stereotype.dto;

import java.util.Map;

public interface PrincipalDto extends Dto {

  String getUsername();

  String getName();

  String getSurname();

  Map<String, Object> getAttributes();
}