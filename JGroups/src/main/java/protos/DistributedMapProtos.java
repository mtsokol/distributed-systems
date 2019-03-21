// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: distributedmap.proto

package protos;

public final class DistributedMapProtos {
  private DistributedMapProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface MapOperationOrBuilder extends
      // @@protoc_insertion_point(interface_extends:MapOperation)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required .MapOperation.OperationType type = 1;</code>
     */
    boolean hasType();
    /**
     * <code>required .MapOperation.OperationType type = 1;</code>
     */
    protos.DistributedMapProtos.MapOperation.OperationType getType();

    /**
     * <code>required string key = 2;</code>
     */
    boolean hasKey();
    /**
     * <code>required string key = 2;</code>
     */
    java.lang.String getKey();
    /**
     * <code>required string key = 2;</code>
     */
    com.google.protobuf.ByteString
        getKeyBytes();

    /**
     * <code>optional int32 value = 3;</code>
     */
    boolean hasValue();
    /**
     * <code>optional int32 value = 3;</code>
     */
    int getValue();
  }
  /**
   * Protobuf type {@code MapOperation}
   */
  public  static final class MapOperation extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:MapOperation)
      MapOperationOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use MapOperation.newBuilder() to construct.
    private MapOperation(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private MapOperation() {
      type_ = 0;
      key_ = "";
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private MapOperation(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {
              int rawValue = input.readEnum();
                @SuppressWarnings("deprecation")
              protos.DistributedMapProtos.MapOperation.OperationType value = protos.DistributedMapProtos.MapOperation.OperationType.valueOf(rawValue);
              if (value == null) {
                unknownFields.mergeVarintField(1, rawValue);
              } else {
                bitField0_ |= 0x00000001;
                type_ = rawValue;
              }
              break;
            }
            case 18: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000002;
              key_ = bs;
              break;
            }
            case 24: {
              bitField0_ |= 0x00000004;
              value_ = input.readInt32();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return protos.DistributedMapProtos.internal_static_MapOperation_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return protos.DistributedMapProtos.internal_static_MapOperation_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              protos.DistributedMapProtos.MapOperation.class, protos.DistributedMapProtos.MapOperation.Builder.class);
    }

    /**
     * Protobuf enum {@code MapOperation.OperationType}
     */
    public enum OperationType
        implements com.google.protobuf.ProtocolMessageEnum {
      /**
       * <code>GET = 0;</code>
       */
      GET(0),
      /**
       * <code>CONTAINS = 1;</code>
       */
      CONTAINS(1),
      /**
       * <code>PUT = 2;</code>
       */
      PUT(2),
      /**
       * <code>REMOVE = 3;</code>
       */
      REMOVE(3),
      ;

      /**
       * <code>GET = 0;</code>
       */
      public static final int GET_VALUE = 0;
      /**
       * <code>CONTAINS = 1;</code>
       */
      public static final int CONTAINS_VALUE = 1;
      /**
       * <code>PUT = 2;</code>
       */
      public static final int PUT_VALUE = 2;
      /**
       * <code>REMOVE = 3;</code>
       */
      public static final int REMOVE_VALUE = 3;


      public final int getNumber() {
        return value;
      }

      /**
       * @deprecated Use {@link #forNumber(int)} instead.
       */
      @java.lang.Deprecated
      public static OperationType valueOf(int value) {
        return forNumber(value);
      }

      public static OperationType forNumber(int value) {
        switch (value) {
          case 0: return GET;
          case 1: return CONTAINS;
          case 2: return PUT;
          case 3: return REMOVE;
          default: return null;
        }
      }

      public static com.google.protobuf.Internal.EnumLiteMap<OperationType>
          internalGetValueMap() {
        return internalValueMap;
      }
      private static final com.google.protobuf.Internal.EnumLiteMap<
          OperationType> internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<OperationType>() {
              public OperationType findValueByNumber(int number) {
                return OperationType.forNumber(number);
              }
            };

      public final com.google.protobuf.Descriptors.EnumValueDescriptor
          getValueDescriptor() {
        return getDescriptor().getValues().get(ordinal());
      }
      public final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptorForType() {
        return getDescriptor();
      }
      public static final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptor() {
        return protos.DistributedMapProtos.MapOperation.getDescriptor().getEnumTypes().get(0);
      }

      private static final OperationType[] VALUES = values();

      public static OperationType valueOf(
          com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != getDescriptor()) {
          throw new java.lang.IllegalArgumentException(
            "EnumValueDescriptor is not for this type.");
        }
        return VALUES[desc.getIndex()];
      }

      private final int value;

      private OperationType(int value) {
        this.value = value;
      }

      // @@protoc_insertion_point(enum_scope:MapOperation.OperationType)
    }

    private int bitField0_;
    public static final int TYPE_FIELD_NUMBER = 1;
    private int type_;
    /**
     * <code>required .MapOperation.OperationType type = 1;</code>
     */
    public boolean hasType() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>required .MapOperation.OperationType type = 1;</code>
     */
    public protos.DistributedMapProtos.MapOperation.OperationType getType() {
      @SuppressWarnings("deprecation")
      protos.DistributedMapProtos.MapOperation.OperationType result = protos.DistributedMapProtos.MapOperation.OperationType.valueOf(type_);
      return result == null ? protos.DistributedMapProtos.MapOperation.OperationType.GET : result;
    }

    public static final int KEY_FIELD_NUMBER = 2;
    private volatile java.lang.Object key_;
    /**
     * <code>required string key = 2;</code>
     */
    public boolean hasKey() {
      return ((bitField0_ & 0x00000002) != 0);
    }
    /**
     * <code>required string key = 2;</code>
     */
    public java.lang.String getKey() {
      java.lang.Object ref = key_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          key_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string key = 2;</code>
     */
    public com.google.protobuf.ByteString
        getKeyBytes() {
      java.lang.Object ref = key_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        key_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int VALUE_FIELD_NUMBER = 3;
    private int value_;
    /**
     * <code>optional int32 value = 3;</code>
     */
    public boolean hasValue() {
      return ((bitField0_ & 0x00000004) != 0);
    }
    /**
     * <code>optional int32 value = 3;</code>
     */
    public int getValue() {
      return value_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasType()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasKey()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) != 0)) {
        output.writeEnum(1, type_);
      }
      if (((bitField0_ & 0x00000002) != 0)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, key_);
      }
      if (((bitField0_ & 0x00000004) != 0)) {
        output.writeInt32(3, value_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) != 0)) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(1, type_);
      }
      if (((bitField0_ & 0x00000002) != 0)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, key_);
      }
      if (((bitField0_ & 0x00000004) != 0)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, value_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof protos.DistributedMapProtos.MapOperation)) {
        return super.equals(obj);
      }
      protos.DistributedMapProtos.MapOperation other = (protos.DistributedMapProtos.MapOperation) obj;

      if (hasType() != other.hasType()) return false;
      if (hasType()) {
        if (type_ != other.type_) return false;
      }
      if (hasKey() != other.hasKey()) return false;
      if (hasKey()) {
        if (!getKey()
            .equals(other.getKey())) return false;
      }
      if (hasValue() != other.hasValue()) return false;
      if (hasValue()) {
        if (getValue()
            != other.getValue()) return false;
      }
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      if (hasType()) {
        hash = (37 * hash) + TYPE_FIELD_NUMBER;
        hash = (53 * hash) + type_;
      }
      if (hasKey()) {
        hash = (37 * hash) + KEY_FIELD_NUMBER;
        hash = (53 * hash) + getKey().hashCode();
      }
      if (hasValue()) {
        hash = (37 * hash) + VALUE_FIELD_NUMBER;
        hash = (53 * hash) + getValue();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static protos.DistributedMapProtos.MapOperation parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static protos.DistributedMapProtos.MapOperation parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static protos.DistributedMapProtos.MapOperation parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static protos.DistributedMapProtos.MapOperation parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static protos.DistributedMapProtos.MapOperation parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static protos.DistributedMapProtos.MapOperation parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static protos.DistributedMapProtos.MapOperation parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static protos.DistributedMapProtos.MapOperation parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static protos.DistributedMapProtos.MapOperation parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static protos.DistributedMapProtos.MapOperation parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static protos.DistributedMapProtos.MapOperation parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static protos.DistributedMapProtos.MapOperation parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(protos.DistributedMapProtos.MapOperation prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code MapOperation}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:MapOperation)
        protos.DistributedMapProtos.MapOperationOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return protos.DistributedMapProtos.internal_static_MapOperation_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return protos.DistributedMapProtos.internal_static_MapOperation_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                protos.DistributedMapProtos.MapOperation.class, protos.DistributedMapProtos.MapOperation.Builder.class);
      }

      // Construct using protos.DistributedMapProtos.MapOperation.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        type_ = 0;
        bitField0_ = (bitField0_ & ~0x00000001);
        key_ = "";
        bitField0_ = (bitField0_ & ~0x00000002);
        value_ = 0;
        bitField0_ = (bitField0_ & ~0x00000004);
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return protos.DistributedMapProtos.internal_static_MapOperation_descriptor;
      }

      @java.lang.Override
      public protos.DistributedMapProtos.MapOperation getDefaultInstanceForType() {
        return protos.DistributedMapProtos.MapOperation.getDefaultInstance();
      }

      @java.lang.Override
      public protos.DistributedMapProtos.MapOperation build() {
        protos.DistributedMapProtos.MapOperation result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public protos.DistributedMapProtos.MapOperation buildPartial() {
        protos.DistributedMapProtos.MapOperation result = new protos.DistributedMapProtos.MapOperation(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          to_bitField0_ |= 0x00000001;
        }
        result.type_ = type_;
        if (((from_bitField0_ & 0x00000002) != 0)) {
          to_bitField0_ |= 0x00000002;
        }
        result.key_ = key_;
        if (((from_bitField0_ & 0x00000004) != 0)) {
          result.value_ = value_;
          to_bitField0_ |= 0x00000004;
        }
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof protos.DistributedMapProtos.MapOperation) {
          return mergeFrom((protos.DistributedMapProtos.MapOperation)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(protos.DistributedMapProtos.MapOperation other) {
        if (other == protos.DistributedMapProtos.MapOperation.getDefaultInstance()) return this;
        if (other.hasType()) {
          setType(other.getType());
        }
        if (other.hasKey()) {
          bitField0_ |= 0x00000002;
          key_ = other.key_;
          onChanged();
        }
        if (other.hasValue()) {
          setValue(other.getValue());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        if (!hasType()) {
          return false;
        }
        if (!hasKey()) {
          return false;
        }
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        protos.DistributedMapProtos.MapOperation parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (protos.DistributedMapProtos.MapOperation) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private int type_ = 0;
      /**
       * <code>required .MapOperation.OperationType type = 1;</code>
       */
      public boolean hasType() {
        return ((bitField0_ & 0x00000001) != 0);
      }
      /**
       * <code>required .MapOperation.OperationType type = 1;</code>
       */
      public protos.DistributedMapProtos.MapOperation.OperationType getType() {
        @SuppressWarnings("deprecation")
        protos.DistributedMapProtos.MapOperation.OperationType result = protos.DistributedMapProtos.MapOperation.OperationType.valueOf(type_);
        return result == null ? protos.DistributedMapProtos.MapOperation.OperationType.GET : result;
      }
      /**
       * <code>required .MapOperation.OperationType type = 1;</code>
       */
      public Builder setType(protos.DistributedMapProtos.MapOperation.OperationType value) {
        if (value == null) {
          throw new NullPointerException();
        }
        bitField0_ |= 0x00000001;
        type_ = value.getNumber();
        onChanged();
        return this;
      }
      /**
       * <code>required .MapOperation.OperationType type = 1;</code>
       */
      public Builder clearType() {
        bitField0_ = (bitField0_ & ~0x00000001);
        type_ = 0;
        onChanged();
        return this;
      }

      private java.lang.Object key_ = "";
      /**
       * <code>required string key = 2;</code>
       */
      public boolean hasKey() {
        return ((bitField0_ & 0x00000002) != 0);
      }
      /**
       * <code>required string key = 2;</code>
       */
      public java.lang.String getKey() {
        java.lang.Object ref = key_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            key_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string key = 2;</code>
       */
      public com.google.protobuf.ByteString
          getKeyBytes() {
        java.lang.Object ref = key_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          key_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string key = 2;</code>
       */
      public Builder setKey(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        key_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string key = 2;</code>
       */
      public Builder clearKey() {
        bitField0_ = (bitField0_ & ~0x00000002);
        key_ = getDefaultInstance().getKey();
        onChanged();
        return this;
      }
      /**
       * <code>required string key = 2;</code>
       */
      public Builder setKeyBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        key_ = value;
        onChanged();
        return this;
      }

      private int value_ ;
      /**
       * <code>optional int32 value = 3;</code>
       */
      public boolean hasValue() {
        return ((bitField0_ & 0x00000004) != 0);
      }
      /**
       * <code>optional int32 value = 3;</code>
       */
      public int getValue() {
        return value_;
      }
      /**
       * <code>optional int32 value = 3;</code>
       */
      public Builder setValue(int value) {
        bitField0_ |= 0x00000004;
        value_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 value = 3;</code>
       */
      public Builder clearValue() {
        bitField0_ = (bitField0_ & ~0x00000004);
        value_ = 0;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:MapOperation)
    }

    // @@protoc_insertion_point(class_scope:MapOperation)
    private static final protos.DistributedMapProtos.MapOperation DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new protos.DistributedMapProtos.MapOperation();
    }

    public static protos.DistributedMapProtos.MapOperation getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    @java.lang.Deprecated public static final com.google.protobuf.Parser<MapOperation>
        PARSER = new com.google.protobuf.AbstractParser<MapOperation>() {
      @java.lang.Override
      public MapOperation parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new MapOperation(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<MapOperation> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<MapOperation> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public protos.DistributedMapProtos.MapOperation getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_MapOperation_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_MapOperation_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\024distributedmap.proto\"\222\001\n\014MapOperation\022" +
      ")\n\004type\030\001 \002(\0162\033.MapOperation.OperationTy" +
      "pe\022\013\n\003key\030\002 \002(\t\022\r\n\005value\030\003 \001(\005\";\n\rOperat" +
      "ionType\022\007\n\003GET\020\000\022\014\n\010CONTAINS\020\001\022\007\n\003PUT\020\002\022" +
      "\n\n\006REMOVE\020\003B\036\n\006protosB\024DistributedMapPro" +
      "tos"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_MapOperation_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_MapOperation_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_MapOperation_descriptor,
        new java.lang.String[] { "Type", "Key", "Value", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}