import { NextFunction, Request, Response } from "express";
var passport = require('passport');
var GoogleStrategy = require('../passport/googleStrategy');

var express = require('express');
var router = express.Router();
passport.use(GoogleStrategy);

/* GET home page. */
router.get('/google', passport.authenticate('google', {scope: ['profile', 'email']}));

router.get('/google/callback', passport.authenticate('google', {failureRedirect: '/'}), function(req:Request, res:Response, next:NextFunction) {
    console.log('성공:', req)
});

module.exports = router;
