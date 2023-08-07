package parser

import com.google.protobuf.DescriptorProtos
import com.google.protobuf.kotlin.toByteStringUtf8
import com.squareup.wire.schema.internal.parser.RpcElement

/**
 * @return DescriptorProtos.MethodDescriptorProto
 */

fun RpcElement.methodDescriptorProto(): DescriptorProtos.MethodDescriptorProto =
    DescriptorProtos.MethodDescriptorProto.newBuilder().let { builder ->
        builder.setName(name)
        builder.setNameBytes(name.toByteStringUtf8())
        builder.setInputType(requestType)
        builder.setInputTypeBytes(requestType.toByteStringUtf8())
        builder.setOutputType(responseType)
        builder.setOutputTypeBytes(responseType.toByteStringUtf8())
        builder.setClientStreaming(requestStreaming)
        builder.setServerStreaming(responseStreaming)
        builder.build()
    }