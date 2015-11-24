package com.example.towerdefense;

/**
 * Created by Brieuc on 21-11-15.
 */

    import android.content.Context;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.graphics.Canvas;
    import android.graphics.Paint;
    import android.graphics.Rect;

    public class Gryphon {

        private static final String TAG = Gryphon.class.getSimpleName();
        private int dy;
        private int dx;

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

        public Gryphon(int x, int y,Context context, int dx, int dy) {
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gryphon_sprite);
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
            this.dx = dx;
            this.dy = dy;
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
            if (this.getDx() == 0){
                if (this.getDy() > 0){
                    this.sourceRect.top = 0;
                    this.sourceRect.bottom = spriteHeight;
                } else {
                    this.sourceRect.top = 3 * spriteHeight;
                    this.sourceRect.bottom = 4 * spriteHeight;
                }
            } else if (this.getDx() > 0){
                this.sourceRect.top = 2 * spriteHeight;
                this.sourceRect.bottom = 3 * spriteHeight;
            } else {
                this.sourceRect.top = spriteHeight;
                this.sourceRect.bottom = 2 * spriteHeight;
            }
        }

        public void draw(Canvas canvas) {
            // where to draw the sprite
            Rect destRect = new Rect(getX() -  + width/2, getY() - height/2, getX() + width/2, getY() + height/2);
            canvas.drawBitmap(bitmap, sourceRect, destRect, null);
            //drawSpriteSelector(canvas);
        }

        private void drawSpriteSelector(Canvas canvas) {
            canvas.drawBitmap(bitmap, 800, 150, null);
            Paint paint = new Paint();
            paint.setARGB(50, 0, 255, 0);
            canvas.drawRect(800 + (currentFrame * sourceRect.width()), 150 + this.sourceRect.top, 800 + (currentFrame * sourceRect.width()) + sourceRect.width(), 150 + this.sourceRect.bottom, paint);

        }

        public int getDx() {
            return dx;
        }

        public int getDy() {
            return dy;
        }

        public void setDx(int dx) {
            this.dx = dx;
        }

        public void setDy(int dy) {
            this.dy = dy;
        }
    }