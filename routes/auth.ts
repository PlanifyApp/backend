import { NextFunction, Request, Response } from "express";
var passport = require('passport');

var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/google', passport.authenticate('google', {scope: ['profile', 'email']}));

router.get('/google/callback', passport.authenticate('google', {failureRedirect: '/'}), function(req:Request, res:Response, next:NextFunction) {
    console.log('성공')
});

module.exports = router;
