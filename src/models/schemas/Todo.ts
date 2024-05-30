import mongoose, { Schema } from "mongoose";
const {
    Types: { ObjectId },
} = Schema;

interface Todo extends Document {
    title: string;
    isDone: string;
    isShow: string;
    userId: typeof ObjectId;
    createAt: Date;
    modifiedAt: Date;
}

const TodoSchema: Schema = new Schema({
    title: {
        type: String,
        required: true,
    },
    isDone: {
        type: String,
        required: false,
        default: "Y",
    },
    isShow: {
        type: String,
        required: false,
        default: "Y",
    },
    userId: {
        type: ObjectId,
        required: true,
        ref: "User",
    },
    createAt: {
        type: Date,
        default: Date.now,
    },
    modifiedAt: {
        type: Date,
        default: Date.now,
    },
});

const Todo = mongoose.model<Todo>("Todo", TodoSchema);
export default Todo;
