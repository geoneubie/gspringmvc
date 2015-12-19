package csb.service.security

import csb.model.security.CurrentUser

interface ICurrentUserService {

    boolean canAccessUser(CurrentUser currentUser, long userId)

}