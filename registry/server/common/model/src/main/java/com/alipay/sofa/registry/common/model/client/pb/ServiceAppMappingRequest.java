/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.registry.common.model.client.pb;

/**
 * Protobuf type {@code ServiceAppMappingRequest}
 */
public final class ServiceAppMappingRequest extends com.google.protobuf.GeneratedMessageV3
        implements
        // @@protoc_insertion_point(message_implements:ServiceAppMappingRequest)
        ServiceAppMappingRequestOrBuilder {
    public static final int SERVICEIDS_FIELD_NUMBER = 1;
    private static final long serialVersionUID = 0L;
    // @@protoc_insertion_point(class_scope:ServiceAppMappingRequest)
    private static final com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest
            DEFAULT_INSTANCE;
    private static final com.google.protobuf.Parser<ServiceAppMappingRequest> PARSER =
            new com.google.protobuf.AbstractParser<ServiceAppMappingRequest>() {
                public ServiceAppMappingRequest parsePartialFrom(
                        com.google.protobuf.CodedInputStream input,
                        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                        throws com.google.protobuf.InvalidProtocolBufferException {
                    return new ServiceAppMappingRequest(input, extensionRegistry);
                }
            };

    static {
        DEFAULT_INSTANCE =
                new com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest();
    }

    private com.google.protobuf.LazyStringList serviceIds_;
    private byte memoizedIsInitialized = -1;

    // Use ServiceAppMappingRequest.newBuilder() to construct.
    private ServiceAppMappingRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }
    private ServiceAppMappingRequest() {
        serviceIds_ = com.google.protobuf.LazyStringArrayList.EMPTY;
    }

    private ServiceAppMappingRequest(
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
                    default: {
                        if (!parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) {
                            done = true;
                        }
                        break;
                    }
                    case 10: {
                        java.lang.String s = input.readStringRequireUtf8();
                        if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
                            serviceIds_ = new com.google.protobuf.LazyStringArrayList();
                            mutable_bitField0_ |= 0x00000001;
                        }
                        serviceIds_.add(s);
                        break;
                    }
                }
            }
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(this);
        } catch (java.io.IOException e) {
            throw new com.google.protobuf.InvalidProtocolBufferException(e).setUnfinishedMessage(this);
        } finally {
            if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
                serviceIds_ = serviceIds_.getUnmodifiableView();
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.alipay.sofa.registry.common.model.client.pb.AppDiscoveryMetaPb
                .internal_static_ServiceAppMappingRequest_descriptor;
    }

    public static com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest parseFrom(
            java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest parseFrom(
            com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest parseFrom(
            byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest parseFrom(
            byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest parseFrom(
            java.io.InputStream input) throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
                PARSER, input, extensionRegistry);
    }

    public static com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest
    parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest
    parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
                PARSER, input, extensionRegistry);
    }

    public static com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest parseFrom(
            com.google.protobuf.CodedInputStream input) throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest parseFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
                PARSER, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(
            com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest prototype) {
        return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    public static com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest
    getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static com.google.protobuf.Parser<ServiceAppMappingRequest> parser() {
        return PARSER;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
    internalGetFieldAccessorTable() {
        return com.alipay.sofa.registry.common.model.client.pb.AppDiscoveryMetaPb
                .internal_static_ServiceAppMappingRequest_fieldAccessorTable
                .ensureFieldAccessorsInitialized(
                        com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest.class,
                        com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest.Builder.class);
    }

    /**
     * <code>repeated string serviceIds = 1;</code>
     */
    public com.google.protobuf.ProtocolStringList getServiceIdsList() {
        return serviceIds_;
    }

    /**
     * <code>repeated string serviceIds = 1;</code>
     */
    public int getServiceIdsCount() {
        return serviceIds_.size();
    }

    /**
     * <code>repeated string serviceIds = 1;</code>
     */
    public java.lang.String getServiceIds(int index) {
        return serviceIds_.get(index);
    }

    /**
     * <code>repeated string serviceIds = 1;</code>
     */
    public com.google.protobuf.ByteString getServiceIdsBytes(int index) {
        return serviceIds_.getByteString(index);
    }

    public final boolean isInitialized() {
        byte isInitialized = memoizedIsInitialized;
        if (isInitialized == 1) return true;
        if (isInitialized == 0) return false;

        memoizedIsInitialized = 1;
        return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
        for (int i = 0; i < serviceIds_.size(); i++) {
            com.google.protobuf.GeneratedMessageV3.writeString(output, 1, serviceIds_.getRaw(i));
        }
        unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
        int size = memoizedSize;
        if (size != -1) return size;

        size = 0;
        {
            int dataSize = 0;
            for (int i = 0; i < serviceIds_.size(); i++) {
                dataSize += computeStringSizeNoTag(serviceIds_.getRaw(i));
            }
            size += dataSize;
            size += 1 * getServiceIdsList().size();
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
        if (!(obj
                instanceof com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest)) {
            return super.equals(obj);
        }
        com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest other =
                (com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest) obj;

        boolean result = true;
        result = result && getServiceIdsList().equals(other.getServiceIdsList());
        result = result && unknownFields.equals(other.unknownFields);
        return result;
    }

    @java.lang.Override
    public int hashCode() {
        if (memoizedHashCode != 0) {
            return memoizedHashCode;
        }
        int hash = 41;
        hash = (19 * hash) + getDescriptor().hashCode();
        if (getServiceIdsCount() > 0) {
            hash = (37 * hash) + SERVICEIDS_FIELD_NUMBER;
            hash = (53 * hash) + getServiceIdsList().hashCode();
        }
        hash = (29 * hash) + unknownFields.hashCode();
        memoizedHashCode = hash;
        return hash;
    }

    public Builder newBuilderForType() {
        return newBuilder();
    }

    public Builder toBuilder() {
        return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        Builder builder = new Builder(parent);
        return builder;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<ServiceAppMappingRequest> getParserForType() {
        return PARSER;
    }

    public com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest
    getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    /**
     * Protobuf type {@code ServiceAppMappingRequest}
     */
    public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
            implements
            // @@protoc_insertion_point(builder_implements:ServiceAppMappingRequest)
            com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequestOrBuilder {
        private int bitField0_;
        private com.google.protobuf.LazyStringList serviceIds_ =
                com.google.protobuf.LazyStringArrayList.EMPTY;

        // Construct using
        // com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest.newBuilder()
        private Builder() {
            maybeForceBuilderInitialization();
        }

        private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
            super(parent);
            maybeForceBuilderInitialization();
        }

        public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
            return com.alipay.sofa.registry.common.model.client.pb.AppDiscoveryMetaPb
                    .internal_static_ServiceAppMappingRequest_descriptor;
        }

        protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
            return com.alipay.sofa.registry.common.model.client.pb.AppDiscoveryMetaPb
                    .internal_static_ServiceAppMappingRequest_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest.class,
                            com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest.Builder
                                    .class);
        }

        private void maybeForceBuilderInitialization() {
            if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
            }
        }

        public Builder clear() {
            super.clear();
            serviceIds_ = com.google.protobuf.LazyStringArrayList.EMPTY;
            bitField0_ = (bitField0_ & ~0x00000001);
            return this;
        }

        public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
            return com.alipay.sofa.registry.common.model.client.pb.AppDiscoveryMetaPb
                    .internal_static_ServiceAppMappingRequest_descriptor;
        }

        public com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest
        getDefaultInstanceForType() {
            return com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest
                    .getDefaultInstance();
        }

        public com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest build() {
            com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest result =
                    buildPartial();
            if (!result.isInitialized()) {
                throw newUninitializedMessageException(result);
            }
            return result;
        }

        public com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest buildPartial() {
            com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest result =
                    new com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest(this);
            int from_bitField0_ = bitField0_;
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                serviceIds_ = serviceIds_.getUnmodifiableView();
                bitField0_ = (bitField0_ & ~0x00000001);
            }
            result.serviceIds_ = serviceIds_;
            onBuilt();
            return result;
        }

        public Builder clone() {
            return (Builder) super.clone();
        }

        public Builder setField(
                com.google.protobuf.Descriptors.FieldDescriptor field, java.lang.Object value) {
            return (Builder) super.setField(field, value);
        }

        public Builder clearField(com.google.protobuf.Descriptors.FieldDescriptor field) {
            return (Builder) super.clearField(field);
        }

        public Builder clearOneof(com.google.protobuf.Descriptors.OneofDescriptor oneof) {
            return (Builder) super.clearOneof(oneof);
        }

        public Builder setRepeatedField(
                com.google.protobuf.Descriptors.FieldDescriptor field, int index, java.lang.Object value) {
            return (Builder) super.setRepeatedField(field, index, value);
        }

        public Builder addRepeatedField(
                com.google.protobuf.Descriptors.FieldDescriptor field, java.lang.Object value) {
            return (Builder) super.addRepeatedField(field, value);
        }

        public Builder mergeFrom(com.google.protobuf.Message other) {
            if (other
                    instanceof com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest) {
                return mergeFrom(
                        (com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest) other);
            } else {
                super.mergeFrom(other);
                return this;
            }
        }

        public Builder mergeFrom(
                com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest other) {
            if (other
                    == com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest
                    .getDefaultInstance()) return this;
            if (!other.serviceIds_.isEmpty()) {
                if (serviceIds_.isEmpty()) {
                    serviceIds_ = other.serviceIds_;
                    bitField0_ = (bitField0_ & ~0x00000001);
                } else {
                    ensureServiceIdsIsMutable();
                    serviceIds_.addAll(other.serviceIds_);
                }
                onChanged();
            }
            this.mergeUnknownFields(other.unknownFields);
            onChanged();
            return this;
        }

        public final boolean isInitialized() {
            return true;
        }

        public Builder mergeFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest parsedMessage = null;
            try {
                parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                parsedMessage =
                        (com.alipay.sofa.registry.common.model.client.pb.ServiceAppMappingRequest)
                                e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } finally {
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
            }
            return this;
        }

        private void ensureServiceIdsIsMutable() {
            if (!((bitField0_ & 0x00000001) == 0x00000001)) {
                serviceIds_ = new com.google.protobuf.LazyStringArrayList(serviceIds_);
                bitField0_ |= 0x00000001;
            }
        }

        /**
         * <code>repeated string serviceIds = 1;</code>
         */
        public com.google.protobuf.ProtocolStringList getServiceIdsList() {
            return serviceIds_.getUnmodifiableView();
        }

        /**
         * <code>repeated string serviceIds = 1;</code>
         */
        public int getServiceIdsCount() {
            return serviceIds_.size();
        }

        /**
         * <code>repeated string serviceIds = 1;</code>
         */
        public java.lang.String getServiceIds(int index) {
            return serviceIds_.get(index);
        }

        /**
         * <code>repeated string serviceIds = 1;</code>
         */
        public com.google.protobuf.ByteString getServiceIdsBytes(int index) {
            return serviceIds_.getByteString(index);
        }

        /**
         * <code>repeated string serviceIds = 1;</code>
         */
        public Builder setServiceIds(int index, java.lang.String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ensureServiceIdsIsMutable();
            serviceIds_.set(index, value);
            onChanged();
            return this;
        }

        /**
         * <code>repeated string serviceIds = 1;</code>
         */
        public Builder addServiceIds(java.lang.String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ensureServiceIdsIsMutable();
            serviceIds_.add(value);
            onChanged();
            return this;
        }

        /**
         * <code>repeated string serviceIds = 1;</code>
         */
        public Builder addAllServiceIds(java.lang.Iterable<java.lang.String> values) {
            ensureServiceIdsIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(values, serviceIds_);
            onChanged();
            return this;
        }

        /**
         * <code>repeated string serviceIds = 1;</code>
         */
        public Builder clearServiceIds() {
            serviceIds_ = com.google.protobuf.LazyStringArrayList.EMPTY;
            bitField0_ = (bitField0_ & ~0x00000001);
            onChanged();
            return this;
        }

        /**
         * <code>repeated string serviceIds = 1;</code>
         */
        public Builder addServiceIdsBytes(com.google.protobuf.ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            checkByteStringIsUtf8(value);
            ensureServiceIdsIsMutable();
            serviceIds_.add(value);
            onChanged();
            return this;
        }

        public final Builder setUnknownFields(final com.google.protobuf.UnknownFieldSet unknownFields) {
            return super.setUnknownFieldsProto3(unknownFields);
        }

        public final Builder mergeUnknownFields(
                final com.google.protobuf.UnknownFieldSet unknownFields) {
            return super.mergeUnknownFields(unknownFields);
        }

        // @@protoc_insertion_point(builder_scope:ServiceAppMappingRequest)
    }
}
