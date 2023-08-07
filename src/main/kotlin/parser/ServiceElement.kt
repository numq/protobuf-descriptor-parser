package parser

import com.google.protobuf.DescriptorProtos
import com.google.protobuf.kotlin.toByteStringUtf8
import com.squareup.wire.schema.internal.parser.RpcElement
import com.squareup.wire.schema.internal.parser.ServiceElement

/**
 * @return DescriptorProtos.ServiceDescriptorProto
 */

fun ServiceElement.serviceDescriptorProto(): DescriptorProtos.ServiceDescriptorProto =
    DescriptorProtos.ServiceDescriptorProto.newBuilder().let { builder ->
        builder.setName(name)
        builder.setNameBytes(name.toByteStringUtf8())
        builder.addAllMethod(rpcs.map(RpcElement::methodDescriptorProto))
        builder.build()
    }