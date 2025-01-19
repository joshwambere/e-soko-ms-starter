package rw.esoko.userservice.contracts

import lombok.AllArgsConstructor
import lombok.Data

@Data
@AllArgsConstructor
class UserDto {
    public var id: String? = null
    public val email: String? = null
    public val password: String? = null
    public var role: String? = null
}