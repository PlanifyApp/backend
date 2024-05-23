import mongoose, { Schema } from "mongoose";
const AutoIncrementFactory = require("mongoose-sequence")(mongoose);
const AutoIncrement = AutoIncrementFactory(mongoose.connection);

export interface Group extends Document {
    name: String;
    color: String;
    is_show: String;
}

const GroupSchema: Schema = new Schema({
    title: {
        type: String,
        required: true,
    },
    color: {
        type: String,
        required: true,
    },
    is_show: {
        type: String,
        required: false,
        default: "Y",
    },
    user_id: {
        type: String,
        required: true,
    },
});

GroupSchema.plugin(AutoIncrement, { inc_field: "id" });
const Group = mongoose.model<Group>("Group", GroupSchema);
export default Group;
