import mongoose from 'mongoose';

export function connectToMongoDB() {
    const mongoUri = process.env.MONGO_URI;

    if (!mongoUri) {
        console.error('MONGO_URI is not defined');
        process.exit(1);
    }

    mongoose.connect(mongoUri, {})
        .then(() => console.log("Connected to MongoDB"))
        .catch(e => console.log("Could not connect to MongoDB"));
}
