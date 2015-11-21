package com.example.towerdefense;

/**
 * Created by Brieuc on 21-11-15.
 */

    import android.graphics.Bitmap;
    import android.graphics.Canvas;
    import android.graphics.Paint;
    import android.graphics.Rect;

    public class Gryphon {

        private static final String TAG = Gryphon.class.getSimpleName();

        private Bitmap bitmap;		// the animation sequence
        private Rect sourceRect;	// the rectangle to be drawn from the animation bitmap
        private int frameNr;		// number of frames in animation
        private int currentFrame;	// the current frame
        private long frameTicker;	// the time of the last frame update
        private int framePeriod;	// milliseconds between each frame (1000/fps)

        private int spriteWidth;	// the width of the sprite to calculate the cut out rectangle
        private int spriteHeight;	// the height of the sprite

        private int width;	// the width of the gryphon
        private int height;	// the height of the gryphon

        private int x;				// the X coordinate of the object (top left of the image)
        private int y;				// the Y coordinate of the object (top left of the image)
        private Speed speed;

        public Gryphon(int x, int y,Bitmap bitmap) {
            this.bitmap = bitmap;
            this.x = x;
            this.y = y;
            this.currentFrame = 0;
            this.frameNr = 4; //framecount
            this.spriteWidth = bitmap.getWidth() / 4;
            this.spriteHeight = bitmap.getHeight() / 4;
            this.sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
            this.width = 200;
            this.height = 200;
            this.framePeriod = 1000 / 5; // 1000/fps
            this.frameTicker = 0l;
            this.speed = new Speed(1,0);
        }


        public Bitmap getBitmap() {
            return bitmap;
        }
        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public int getX() {
            return x;
        }
        public void setX(int x) {
            this.x = x;
        }
        public int getY() {
            return y;
        }
        public void setY(int y) {
            this.y = y;
        }

        public void update(long gameTime) {
            if (gameTime > frameTicker + framePeriod) {
                frameTicker = gameTime;
                // increment the frame
                currentFrame++;
                if (currentFrame >= frameNr) {
                    currentFrame = 0;
                }
            }
            // define the rectangle to cut out sprite
            this.sourceRect.left = currentFrame * spriteWidth;
            this.sourceRect.right = this.sourceRect.left + spriteWidth;
            if (this.getSpeed().getxDirection() == 0){
                if (this.getSpeed().getyDirection() == 1){
                    this.sourceRect.top = 0;
                    this.sourceRect.bottom = spriteHeight;
                } else {
                    this.sourceRect.top = 3 * spriteHeight;
                    this.sourceRect.bottom = 4 * spriteHeight;
                }
            } else if (this.getSpeed().getxDirection() == 1){
                this.sourceRect.top = 2 * spriteHeight;
                this.sourceRect.bottom = 3 * spriteHeight;
            } else {
                this.sourceRect.top = spriteHeight;
                this.sourceRect.bottom = 2 * spriteHeight;
            }

        }

        public void draw(Canvas canvas) {
            // where to draw the sprite
            Rect destRect = new Rect(getX(), getY(), getX() + width, getY() + height);
            canvas.drawBitmap(bitmap, sourceRect, destRect, null);
            drawSpriteSelector(canvas);
        }

        private void drawSpriteSelector(Canvas canvas) {
            canvas.drawBitmap(bitmap, 600, 150, null);
            Paint paint = new Paint();
            canvas.drawRect(600 + (currentFrame * sourceRect.width()), 150 + this.sourceRect.top, 600 + (currentFrame * sourceRect.width()) + sourceRect.width(), 150 + this.sourceRect.bottom,  paint);

        }

        public Speed getSpeed() {
            return speed;
        }
    }