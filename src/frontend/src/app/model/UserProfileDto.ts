export class UserProfileDto {
    profileId: number;
    firstname: string;
    lastname: string;
    photo: string;
    username: string;

    constructor(private _username: string) {
        this.username = _username;
    }
}
