package com.teammental.meproxy.ui.editor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teammental.mecore.stereotype.dto.Dto;
import com.teammental.mehelper.StringHelper;
import com.teammental.memapper.util.mapping.CommonMapUtil;
import com.teammental.meproxy.ui.editor.dto.DateTimeEditorFieldDto;
import com.teammental.meproxy.ui.editor.dto.EditorFieldDto;
import com.teammental.meproxy.ui.editor.dto.EditorFieldDtoFactory;
import com.teammental.meproxy.ui.editor.dto.EditorViewDto;
import com.teammental.meproxy.ui.editor.dto.FileImageUploadEditorFieldDto;
import com.teammental.meproxy.ui.editor.dto.FileUploadEditorFieldDto;
import com.teammental.meproxy.ui.editor.dto.SelectSingleFillByTriggerAjaxEditorFieldDto;
import com.teammental.meproxy.ui.editor.dto.SelectSingleFillFromAjaxEditorFieldDto;
import com.teammental.meproxy.ui.editor.dto.SelectSingleFillFromModelEditorFieldDto;
import com.teammental.meproxy.ui.editor.editortemplate.EditorTemplate;
import com.teammental.meproxy.ui.editor.editortemplate.FileUploadEditor;
import com.teammental.meproxy.ui.editor.editortemplate.FileUploadImageEditor;
import com.teammental.meproxy.ui.editor.editortemplate.SelectSingleFillByTriggerAjaxEditor;
import com.teammental.meproxy.ui.editor.editortemplate.SelectSingleFillFromAjaxEditor;
import com.teammental.meproxy.ui.editor.editortemplate.SelectSingleFillFromModelEditor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("PMD")
public class EditorViewDtoAdapterDefaultImpl
    implements EditorViewDtoAdapter {

  private static final Logger LOGGER
      = LoggerFactory.getLogger(EditorViewDtoAdapterDefaultImpl.class);

  private List<Field> fields;

  /**
   * Constructor.
   *
   * @param clazz class
   */
  public EditorViewDtoAdapterDefaultImpl(Class<? extends Dto> clazz) {

    fields = getEditorTemplateFields(clazz);
  }

  @Override
  public EditorViewDto adapt(Dto dto) {

    EditorViewDto editorViewDto = new EditorViewDto();
    editorViewDto.setEditorFieldDtos(convertToEditorFieldDtos(dto));

    logEditorViewDto(editorViewDto);

    return editorViewDto;
  }

  private List<Field> getEditorTemplateFields(Class<?> clazz) {

    List<Field> allFields = CommonMapUtil.getAllFields(clazz);

    List<Field> fields = allFields
        .stream()
        .filter(field -> Arrays.stream(field.getAnnotations())
            .anyMatch(annotation -> annotation.annotationType()
                .isAnnotationPresent(EditorTemplate.class)))
        .collect(Collectors.toList());

    return fields;
  }

  private List<EditorFieldDto> convertToEditorFieldDtos(Dto dto) {

    Map<String, Object> fieldsMap = CommonMapUtil.getFieldsMap(dto);

    List<EditorFieldDto> editorFieldDtos =
        fields
            .stream()
            .map(field -> convertToFieldDto(field, fieldsMap))
            .collect(Collectors.toList());

    return editorFieldDtos;
  }

  private EditorFieldDto convertToFieldDto(Field field, Map<String, Object> fieldsMap) {

    Annotation annotation = Arrays
        .stream(field.getAnnotations())
        .filter(an -> an.annotationType().isAnnotationPresent(EditorTemplate.class))
        .findFirst().get();

    String name = annotation
        .annotationType().getAnnotation(EditorTemplate.class)
        .name();

    String labelNameTemplate = field.getName();
    Object value = fieldsMap.get(labelNameTemplate);

    EditorFieldDto editorFieldDto = EditorFieldDtoFactory.getEditorField(annotation);
    editorFieldDto.setTemplateName(name);
    editorFieldDto.setFieldName(labelNameTemplate);
    editorFieldDto.setInputValue(value);
    editorFieldDto.setAttributes(getCustomAttributes(field, annotation, value,
        fieldsMap));

    setSpecificProperties(editorFieldDto, field,
        fieldsMap);

    return editorFieldDto;
  }

  private void setSpecificProperties(EditorFieldDto editorFieldDto,
                                     Field field,
                                     Map<String, Object> fieldsMap) {

    if (editorFieldDto instanceof DateTimeEditorFieldDto) {
      DateTimeFormat dateTimeFormat
          = field.getAnnotation(DateTimeFormat.class);

      DateTimeEditorFieldDto dateTimeEditorFieldDto
          = (DateTimeEditorFieldDto) editorFieldDto;

      dateTimeEditorFieldDto.setFormatExpression(StringHelper
          .isNullOrEmpty(dateTimeFormat.pattern())
          ? "dd.MM.yyyy"
          : dateTimeFormat.pattern());
    } else if (editorFieldDto instanceof SelectSingleFillFromModelEditorFieldDto) {

      SelectSingleFillFromModelEditor selectSingleEditor
          = field.getAnnotation(SelectSingleFillFromModelEditor.class);

      SelectSingleFillFromModelEditorFieldDto selectSingleEditorFieldDto
          = (SelectSingleFillFromModelEditorFieldDto) editorFieldDto;

      selectSingleEditorFieldDto
          .setModelAttributeForSelectList(selectSingleEditor.modelAttributeForSelectList());

    } else if (editorFieldDto instanceof SelectSingleFillFromAjaxEditor) {

      SelectSingleFillFromAjaxEditor selectSingleEditor
          = field.getAnnotation(SelectSingleFillFromAjaxEditor.class);

      SelectSingleFillFromAjaxEditorFieldDto selectSingleEditorFieldDto
          = (SelectSingleFillFromAjaxEditorFieldDto) editorFieldDto;

      selectSingleEditorFieldDto
          .setAjaxUrl(selectSingleEditor.ajaxUrl());

      selectSingleEditorFieldDto.setEnableAjaxSearch(selectSingleEditor.enableAjaxSearch());

      if (selectSingleEditor.enableAjaxSearch()) {

        String tempFieldNameForValue
            = field.getName().replace("Id", "Name");

        if (fieldsMap.containsKey(tempFieldNameForValue)
            && fieldsMap.get(tempFieldNameForValue) != null) {
          String tempValue
              = fieldsMap.get(tempFieldNameForValue).toString();

          selectSingleEditorFieldDto.setPlaceHolder(tempValue);
        }
      }

    } else if (editorFieldDto instanceof SelectSingleFillByTriggerAjaxEditor) {

      SelectSingleFillByTriggerAjaxEditor selectSingleEditor
          = field.getAnnotation(SelectSingleFillByTriggerAjaxEditor.class);

      SelectSingleFillByTriggerAjaxEditorFieldDto selectSingleEditorFieldDto
          = (SelectSingleFillByTriggerAjaxEditorFieldDto) editorFieldDto;

      selectSingleEditorFieldDto
          .setAjaxUrl(selectSingleEditor.ajaxUrl());

      selectSingleEditorFieldDto.setTriggeredBy(selectSingleEditor.triggeredBy());

      selectSingleEditorFieldDto.setEnableAjaxSearch(selectSingleEditor.enableAjaxSearch());

    } else if (editorFieldDto instanceof FileUploadEditor) {
      FileUploadEditor fileUploadEditor
          = (FileUploadEditor) editorFieldDto;

      FileUploadEditorFieldDto fileUploadEditorFieldDto
          = (FileUploadEditorFieldDto) editorFieldDto;

      Object existingFileData = fieldsMap.get(fileUploadEditor.existingFileFieldName());
      if (existingFileData != null) {
        fileUploadEditorFieldDto.setExistingFile((byte[]) existingFileData);
      }
    } else if (editorFieldDto instanceof FileUploadImageEditor) {
      FileUploadImageEditor fileUploadImageEditor
          = (FileUploadImageEditor) editorFieldDto;

      FileImageUploadEditorFieldDto fileImageUploadEditorFieldDto
          = (FileImageUploadEditorFieldDto) editorFieldDto;

      Object existingFileData = fieldsMap.get(fileUploadImageEditor.existingFileFieldName());
      if (existingFileData != null) {
        fileImageUploadEditorFieldDto.setExistingFile((byte[]) existingFileData);
      }
    }
  }

  @SuppressWarnings("PMD")
  private String getCustomAttributes(Field field, Annotation annotation, Object value,
                                     Map<String, Object> fieldsMap) {

    Map<String, String> attributesMap
        = new HashMap<>();

    if (field.isAnnotationPresent(NotNull.class)
        || field.isAnnotationPresent(NotBlank.class)
        || field.isAnnotationPresent(NotEmpty.class)) {
      attributesMap.put("required", "required");
    }

    if (field.isAnnotationPresent(DateTimeFormat.class)) {
      DateTimeFormat dateTimeFormat
          = field.getAnnotation(DateTimeFormat.class);

      attributesMap.put("data-date-format",
          StringHelper.isNullOrEmpty(dateTimeFormat.pattern())
              ? "dd.mm.yyyy"
              : dateTimeFormat.pattern().toLowerCase());
    }

    if (field.isAnnotationPresent(Size.class)) {
      Size size
          = field.getAnnotation(Size.class);

      if (size.max() > 0) {
        attributesMap.put("maxlength", String.valueOf(size.max()));
      }
    }

    {
      if (field.isAnnotationPresent(Past.class)) {
        attributesMap.put("data-date-end-date", "0d");
      }
    }

    if (annotation instanceof SelectSingleFillFromModelEditor) {

      SelectSingleFillFromModelEditor selectSingleFillFromModelEditor
          = (SelectSingleFillFromModelEditor) annotation;
      setSelectSingleFromModelAttributes(attributesMap, value,
          selectSingleFillFromModelEditor.activateSelect2());

    } else if (annotation instanceof SelectSingleFillFromAjaxEditor) {

      setSelectSingleFromAjax(attributesMap, value, annotation, fieldsMap, field);

    } else if (annotation instanceof SelectSingleFillByTriggerAjaxEditor) {

      setSelectSingleTriggeredAjax(attributesMap, value, annotation);
    }

    String attributes = attributesMap.keySet().isEmpty()
        ? "data-has-custom-attribute='no'"
        : attributesMap.keySet()
        .stream()
        .reduce(",", (s, s2) -> s + "," + s2 + "='" + attributesMap.get(s2) + "'")
        .replace(",,", "");

    return attributes;
  }

  private void setSelectSingleTriggeredAjax(Map<String, String> attributesMap,
                                            Object value,
                                            Annotation annotation) {

    SelectSingleFillByTriggerAjaxEditor selectSingleEditor
        = (SelectSingleFillByTriggerAjaxEditor) annotation;

    addDefaultSelectSingleAttributes(attributesMap, value, true);

    attributesMap.put("data-tm-select2-triggered-ajax", "true");

    attributesMap.put("data-tm-select2-triggered-by", Arrays.stream(selectSingleEditor
        .triggeredBy()).reduce(",", (s, s2) -> s + "," + s2).replace(",,", ""));

    attributesMap.put("data-tm-select2-triggered-ajax-url",
        selectSingleEditor.ajaxUrl());

    if (selectSingleEditor.enableAjaxSearch()) {
      attributesMap.put("data-tm-select2-triggered-ajax-enable-search", "true");
    } else {
      attributesMap.put("data-tm-select2-triggered-ajax-enable-search", "false");
    }
  }

  private void setSelectSingleFromAjax(Map<String, String> attributesMap,
                                       Object value,
                                       Annotation annotation,
                                       Map<String, Object> fieldsMap,
                                       Field field) {

    SelectSingleFillFromAjaxEditor selectSingleEditor
        = (SelectSingleFillFromAjaxEditor) annotation;


    addDefaultSelectSingleAttributes(attributesMap, value, true);

    attributesMap.put("data-tm-select2-ajax", "true");
    attributesMap.put("data-tm-select2-ajax-url",
        selectSingleEditor.ajaxUrl());


    if (selectSingleEditor.enableAjaxSearch()) {

      String tempFieldNameForValue
          = field.getName().replace("Id", "Name");

      if (fieldsMap.containsKey(tempFieldNameForValue)
          && fieldsMap.get(tempFieldNameForValue) != null) {
        String tempValue
            = fieldsMap.get(tempFieldNameForValue).toString();

        attributesMap.put("data-placeholder", tempValue);
      }
    }


    if (selectSingleEditor.enableAjaxSearch()) {
      attributesMap.put("data-tm-select2-ajax-enable-search", "true");
    } else {
      attributesMap.put("data-tm-select2-ajax-enable-search", "false");
    }
  }

  private void setSelectSingleFromModelAttributes(Map<String,
      String> attributesMap, Object value, boolean activateSelect2) {

    addDefaultSelectSingleAttributes(attributesMap, value, activateSelect2);

    attributesMap.put("data-tm-select2-ajax", "false");

    attributesMap.put("data-tm-select2-ajax-enable-search", "false");
  }

  private void addDefaultSelectSingleAttributes(Map<String, String> attributesMap,
                                                Object value,
                                                boolean activateSelect2) {

    attributesMap.put("data-tm-select2", String.valueOf(activateSelect2));

    if (value != null) {
      attributesMap.put("data-tm-select2-selected-id", value.toString());
    } else {
      attributesMap.put("data-tm-select2-selected-id", "");
    }
  }

  private void logEditorViewDto(EditorViewDto editorViewDto) {

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      String logMessage = objectMapper.writeValueAsString(editorViewDto);
      LOGGER.debug("EditorViewDto Value: " + logMessage);
    } catch (JsonProcessingException ex) {
      LOGGER.debug("EditorViewDto Value couldn't serialized");
    }
  }
}
