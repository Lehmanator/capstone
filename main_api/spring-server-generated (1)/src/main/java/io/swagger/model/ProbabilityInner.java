package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ProbabilityInner
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-26T03:53:34.812Z")

public class ProbabilityInner   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("class_name")
  private String className = null;

  @JsonProperty("class_probability")
  private String classProbability = null;

  public ProbabilityInner id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(example = "dd43aa36-9656-494e-92af-e8a41c66f11a", value = "")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ProbabilityInner className(String className) {
    this.className = className;
    return this;
  }

   /**
   * Get className
   * @return className
  **/
  @ApiModelProperty(example = "PSU", value = "")
  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public ProbabilityInner classProbability(String classProbability) {
    this.classProbability = classProbability;
    return this;
  }

   /**
   * Get classProbability
   * @return classProbability
  **/
  @ApiModelProperty(example = "0.567", value = "")
  public String getClassProbability() {
    return classProbability;
  }

  public void setClassProbability(String classProbability) {
    this.classProbability = classProbability;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProbabilityInner probabilityInner = (ProbabilityInner) o;
    return Objects.equals(this.id, probabilityInner.id) &&
        Objects.equals(this.className, probabilityInner.className) &&
        Objects.equals(this.classProbability, probabilityInner.classProbability);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, className, classProbability);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProbabilityInner {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    className: ").append(toIndentedString(className)).append("\n");
    sb.append("    classProbability: ").append(toIndentedString(classProbability)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

