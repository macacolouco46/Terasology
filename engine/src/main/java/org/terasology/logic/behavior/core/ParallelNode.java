/*
 * Copyright 2017 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.logic.behavior.core;

/**
 * Runs all children parallel.
 */
public class ParallelNode extends CompositeNode {

    private enum Policy {
        RequireOne,
        RequireAll
    }

    private Policy policy = Policy.RequireOne;

    @Override
    public String getName() {
        return "parallel";
    }

    @Override
    public BehaviorNode deepCopy() {
        ParallelNode result = new ParallelNode();
        for (BehaviorNode child : children) {
            result.children.add(child.deepCopy());
        }
        return result;
    }

    @Override
    public void construct(Actor actor) {
        for (BehaviorNode child : children) {
            child.construct(actor);
        }
    }

    @Override
    public BehaviorState execute(Actor actor) {
        int successCounter = 0;
        for (BehaviorNode child : children) {
            BehaviorState result = child.execute(actor);
            if (result == BehaviorState.FAILURE) {
                return BehaviorState.FAILURE;
            }
            if (result == BehaviorState.SUCCESS) {
                successCounter++;
            }
        }
        return policy == Policy.RequireAll ?
                successCounter == children.size() ? BehaviorState.SUCCESS : BehaviorState.RUNNING :
                successCounter > 0 ? BehaviorState.SUCCESS : BehaviorState.RUNNING;
    }

    @Override
    public void destruct(Actor actor) {
        for (BehaviorNode child : children) {
            child.destruct(actor);
        }
    }

}
