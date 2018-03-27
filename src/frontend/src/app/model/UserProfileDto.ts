export class UserProfileDto {
    public profileId: number;
    public firstname: string;
    public lastname: string;
    public photo: string;
    public username: string;

    constructor(private _username: string) {
        this.username = _username;
    }
}