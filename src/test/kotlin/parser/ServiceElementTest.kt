package parser

import com.google.protobuf.DescriptorProtos
import com.squareup.wire.schema.Location
import com.squareup.wire.schema.internal.parser.RpcElement
import com.squareup.wire.schema.internal.parser.ServiceElement
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ServiceElementTest {
    @Test
    fun returnsDescriptor() {
        val name = "name"
        val requestType = "request"
        val responseType = "response"
        val method = DescriptorProtos.MethodDescriptorProto.newBuilder().let { builder ->
            builder.setName(name)
            builder.setInputType(requestType)
            builder.setOutputType(responseType)
            builder.setClientStreaming(true)
            builder.setServerStreaming(true)
            builder.build()
        }
        val descriptor = DescriptorProtos.ServiceDescriptorProto.newBuilder().let { builder ->
            builder.setName(name)
            builder.addMethod(method)
            builder.build()
        }
        val serviceElement = ServiceElement(
            Location.get(""), name, rpcs = listOf(
                RpcElement(
                    Location.get(""),
                    name = name,
                    requestType = requestType,
                    responseType = responseType,
                    requestStreaming = true,
                    responseStreaming = true
                )
            )
        )
        assertEquals(descriptor, serviceElement.serviceDescriptorProto())
    }
}