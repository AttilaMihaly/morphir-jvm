/*
Copyright 2020 Morgan Stanley

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/


package morphir.ir.sdk

import morphir.ir.documented
import morphir.ir.module.ModulePath
import morphir.ir.{ ModuleSpecification, Name }
import morphir.ir.Type.Reference
import morphir.ir.Type.Specification.OpaqueTypeSpecification

object String {
  val moduleName: ModulePath =
    ModulePath.fromString("String")

  val moduleSpec: ModuleSpecification[Unit] = ModuleSpecification(
    Map(
      Name.fromString("String") -> documented(
        "Type that represents a string of characters.",
        OpaqueTypeSpecification(List.empty)
      )
    ),
    Map.empty
  )

  def stringType[A](attributes: A): Reference[A] =
    Reference(attributes, Common.toFQName(moduleName, "String"), List.empty)

  @inline def stringType: Reference[Unit] =
    stringType(())
}
